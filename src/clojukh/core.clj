(ns clojukh.core
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
          crashrpt (get (:params req) "crashrpt")]
      (send-message {:host (:smtp (:email config))
                     :user (:from (:email config))
                     :pass (:pass (:email config))
                     :ssl :yes!!!11}
                    {:from (:from (:email config))
                     :to (:to (:email config))
                     :subject (format "Crash report (%s %s)" appname appversion) 
                     :body [{:type "text/plain"
                             :content "Look at the file attached"}
                            {:type :attachment
                             :content (:tempfile crashrpt)}]}))))

(defn -main []
  (run-jetty
    (-> clojukh-routes
        wrap-multipart-params) {:http? false
                                :ssl? true
                                :ssl-port (:port (:general config))
                                :keystore (:keystore (:general config))
                                :key-password (:key-password (:general config))}))
