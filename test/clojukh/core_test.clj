(ns clojukh.core-test
  (:require [clojure.test :refer :all]
            [clojukh.core :refer :all]))

(deftest config-test
  (testing "contains all the required fields"
    (is (every? #(get-in config %) [[:general :port]
                                    [:general :keystore]
                                    [:general :key-password]
                                    [:general :crashrpts-dir]
                                    [:email :smtp]
                                    [:email :from]
                                    [:email :pass]
                                    [:email :to]]))))
