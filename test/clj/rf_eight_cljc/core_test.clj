(ns rf-eight-cljc.core-test
  (:require [clojure.test :refer [deftest is]]
            [cheshire.core :as json]
            [re-frame.db :refer [app-db]]
            re-frame.core
            [re-frame.test-support :as rf]
            rf-eight-cljc.handlers
            rf-eight-cljc.subs
            rf-eight-cljc.views
            [rf-eight-cljc.core :as server]))

(re-frame.core/def-fx
  :http
  (fn [{:keys [uri method payload on-success-v]}]
    (println "http payload: " payload)
    (let [response  (server/handler {:uri            uri
                                     :request-method method
                                     :body           (json/encode payload)})
          resp-body (json/decode (:body response))]
      (re-frame.core/dispatch (conj on-success-v resp-body)))))

(deftest update-name
  (rf/reset-app-db! [:initialize-db])
  (is (= @app-db {:name "a", :seen []}))
  (rf/dispatch>!! [:update-name "foo"])
  (is (= @app-db {:name "foo", :seen []})))


(deftest simple-post
  (reset! server/seen [])
  (rf/reset-app-db! [:initialize-db])
  (rf/dispatch>!! [:update-name "foo"])
  (rf/dispatch>!! [:submit-name])
  (is (= @server/seen ["foo"]))
  (is (= @app-db {:name 1 :seen ["foo"]})))
