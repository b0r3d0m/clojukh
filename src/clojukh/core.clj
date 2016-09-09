(ns clojukh.core
  (:require [clojure.tools.logging :as log])
  (:require [clojure.java.io :as io])
  (:require clojure.edn)
  (use compojure.core)
  (use postal.core)
  (use ring.adapter.jetty)
  (use ring.middleware.multipart-params))

(def config (clojure.edn/read-string (slurp "config.edn")))

(defroutes clojukh-routes
  (POST "/crashrpt" req
    (let [appname (get (:params req) "appname")
          appversion (get (:params req) "appversion")
          crashrpt (get (:params req) "crashrpt")
          md5 (get (:params req) "md5")
          outputfile (io/file (:crashrpts-dir (:general config)) (:filename crashrpt))]
      (log/infof "Application %s_%s crashed, see %s for details" appname appversion (:filename crashrpt))
      (io/copy (:tempfile crashrpt) outputfile)
      (send-message {:host (:smtp (:email config))
                     :user (:from (:email config))
                     :pass (:pass (:email config))
                     :ssl :yes!!!11}
                    {:from (:from (:email config))
                     :to (:to (:email config))
                     :subject (format "Crash report (%s %s)" appname appversion)
                     :body [{:type "text/plain"
                             :content (str "Look at the file attached.\nMD5: " md5)}
                            {:type :attachment
                             :content outputfile}]}))))

(defn -main []
  (run-jetty
    (-> clojukh-routes
        wrap-multipart-params) {:http? false
                                :ssl? true
                                :ssl-port (:port (:general config))
                                :keystore (:keystore (:general config))
                                :key-password (:key-password (:general config))}))
