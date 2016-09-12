FROM pandeiro/lein
MAINTAINER b0r3d0m <b0r3d0mness@gmail.com>
RUN apt-get update && apt-get install -y git
RUN git clone https://github.com/b0r3d0m/clojukh.git && cd clojukh && lein deps
WORKDIR /app/clojukh
ENTRYPOINT lein run
