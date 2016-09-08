(defproject clojukh "1.0.0"
  :description "A simple CrashRpt server written in Clojure"
  :url "https://github.com/b0r3d0m/clojukh"
  :license {:name "ISC License"
            :url "https://opensource.org/licenses/ISC"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring "1.5.0"]
                 [com.draines/postal "2.0.0"]]
  :main clojukh.core)
