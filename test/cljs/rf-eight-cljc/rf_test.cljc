(ns rf-eight-cljc.rf-test
  (:require
   #?(:clj [clojure.test :as t]
      :cljs [cljs.test :as t :include-macros true])
   #?(:cljs [re-frame.core :as rf])
   #?(:cljs [re-frame.db :refer [app-db]])))


(def default-test-db {:a 1})

(t/use-fixtures :each
  (fn reset-app-db [t]
    (let [prev-app-db @app-db
          _ (reset! app-db default-test-db)
          test-result  (t)]
      (reset! app-db prev-app-db)
      test-result)))

(t/deftest registering-event
  (rf/def-event :init (fn [_ _] default-test-db))
  (t/is (not (nil? (re-frame.events/lookup-handler :init)))
        "A handler function was assoc'd into id->fn."))

(t/deftest dispatching
  (rf/dispatch-sync [:init])
  (t/is (= {:a 1} @app-db)))

(t/deftest registering-subscription
  (rf/def-sub :aye (fn [db] (:a db))))

(t/deftest subscription
  (t/is (= 1 @(rf/subscribe [:aye]))))

(t/deftest event-with-mw
  (rf/def-event :local-set-kv
    [(rf/path :local) rf/trim-v]
    (fn [local [k v]] (assoc local k v)))

  (rf/dispatch-sync [:local-set-kv :x 2])
  (t/is (= {:a 1, :local {:x 2}} @app-db)))


(defn run [] (t/run-all-tests))
