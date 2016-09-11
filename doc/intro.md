# Introduction to clojukh

clojukh is a simple [CrashRpt](http://crashrpt.sourceforge.net/) server written in [Clojure](https://clojure.org/).

It works as a "proxy" in sense that it redirects crash reports to an e-mail provided in a config while saving them to the disk as well.

## Why clojukh?

Well, generally it's not a good idea to use CrashRpt's built-in e-mail functionality because it sucks in the following ways:

* MAPI sucks because it requires an e-mail client installed on your client's computer.
* CrashRpt's SMTP sucks 'cause it doesn't support TLS and you don't want to put your GMail's login and password in the app.
* Also note that just sending an e-mail is not enough -- most e-mail services limit the size of attachments making it unusable for large dump files.

So now you should consider using HTTP instead.

But what's the point of writing your own CrashRpt server instead of using the ready-made solution?

## Usage

Please refer to the [README](README.md) file for details on how to use clojukh.

## API details

The only route that clojukh defines is "POST /crashrpt".

It expects the "multipart/form-data" request in the format described in the CrashRpt's [documentation](http://crashrpt.sourceforge.net/docs/html/sending_error_reports.html).

It returns 200 OK (with the body being set to "Done") in case of success, or 500 Internal Server Error (with the body equal to "Internal error") otherwise.

## NOTEs

* You are forced to use secure connections for both HTTP and SMTP.
* Note that CrashRpt does NOT work with self-signed certificates out-of-the-box. You either have to add SECURITY_FLAG_IGNORE_UNKNOWN_CA flag and re-compile the crashsender project or issue a trusted certificate (this is the recommened way).
* Note that you have to convert your certificates to the JKS (Java KeyStore) format to be able to use them with clojukh.

## How to contribute

* Fork this repository
* Make changes
* Create a pull request

Feel free to report any bugs or post your suggestions!
