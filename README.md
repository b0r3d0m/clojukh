# clojukh

![clojukh logo](http://i.imgur.com/RoWk1zy.jpg)

[![Build Status](https://travis-ci.org/b0r3d0m/clojukh.svg?branch=dev)](https://travis-ci.org/b0r3d0m/clojukh)

A simple [CrashRpt](http://crashrpt.sourceforge.net/) server written in [Clojure](https://clojure.org/).

It redirects crash reports to an e-mail provided in a config.

## Usage

* git clone
* Issue SSL certificate (you can use [SSL For Free](https://www.sslforfree.com/) as a quick start)
* vim config.edn
* Install [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* Install [Leiningen](http://leiningen.org/)
* lein run
* Crash you application like this:

```cpp
/**
 * Ребята не стоит вскрывать этот код. Вы молодые, шутливые, вам все легко.
 * Это не то.
 * Это не Чикатило и даже не архивы спецслужб. Сюда лучше не лезть.
 * Серьезно, любой из вас будет жалеть. Лучше закройте код и забудьте что тут писалось.
 * Я вполне понимаю что данным сообщением вызову дополнительный интерес, но хочу сразу предостеречь пытливых - стоп.
 * Остальные просто не найдут...
 */

#include <CrashRpt.h>

#include <boost/scope_exit.hpp>

#include <cstring>
#include <iostream>
 
#pragma comment(lib, "CrashRpt1403.lib")

int main()
{
  CR_INSTALL_INFOA info;
  std::memset(&info, 0, sizeof(CR_INSTALL_INFOA));
  info.cb = sizeof(CR_INSTALL_INFOA);
  info.pszAppName = "helper";                 // FIXME
  info.pszAppVersion = "1.0.0";               // FIXME
  info.pszUrl = "https://127.0.0.1/crashrpt"; // FIXME
  info.dwFlags |= CR_INST_ALL_POSSIBLE_HANDLERS;
  // From the documentation:
  // "It is not recommended to use this flag for regular GUI-based applications, blah-blah-blah"
  // Do not tell me what to do, man!
  info.dwFlags |= CR_INST_NO_GUI;
  info.uPriorities[CR_HTTP] = 1;                     // Use HTTP.
  info.uPriorities[CR_SMTP] = CR_NEGATIVE_PRIORITY;  // Not use SMTP.
  info.uPriorities[CR_SMAPI] = CR_NEGATIVE_PRIORITY; // Not use Simple MAPI.
  int res = crInstallA(&info);
  if (res != 0)
  {
    char err_msg[512]; // Feel the magic! (∩｀-´)⊃━☆ﾟ.*･｡ﾟ
    if (crGetLastErrorMsgA(err_msg, sizeof(err_msg) / sizeof(*err_msg)) > 0)
    {
      std::cerr << "Unable to initialize crashRpt library: " << err_msg << std::endl;
    }
    return EXIT_FAILURE;
  }
  BOOST_SCOPE_EXIT_ALL()
  {
    (void)crUninstall(); // Yeah, that's right -- return codes are for pussies
  };

  // Do NOT do this at home!
  // NOTE FOR COMPILER:
  // BEEP BEEP
  // PLEASE DO NOT OPTIMIZE THE FOLLOWING CODE, I REALLY WANT MY APPLICATION TO CRASH
  int* ptr = nullptr;
  *ptr = 1;
  std::cout << *ptr << std::endl;
  // THANKS MR. COMPILER
  // END OF NON-OPTIMIZED SECTION
}
```

### Docker

Alternatively, you can use :whale: [Docker](https://www.docker.com)'s [clojukh image](https://hub.docker.com/r/b0r3d0m/clojukh/) to avoid installing the whole environment by yourself. Please follow the instructions at the specified link to get it work.

## Run tests

* lein test

It's pretty simple, isn't it?

## Docs

Docs can be found [here](doc/intro.md)

## TODO

* Write more tests (maybe the mocked ones)

## License

Copyright © 2016 b0r3d0m <b0r3d0mness@gmail.com>

Distributed under the ISC License.
