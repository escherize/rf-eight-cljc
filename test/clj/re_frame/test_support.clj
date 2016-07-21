(ns re-frame.test-support
  (:require [re-frame.core :as rf]
            [re-frame.db]
            [re-frame.router :as router]))

(defn reset-app-db!
  ([]
   (reset! re-frame.db/app-db {}))
  ([dispatch-v]
   (reset-app-db!)
   (rf/dispatch-sync dispatch-v)))

(defn pump-event-queue!
  "Run until queue is empty, maximum X times."
  []
  ;; FIXME oh so dirty
  (Thread/sleep 50)
  #_(doseq [_ (range 5)]
      (try
        (router/-run-queue router/event-queue)
        (catch Exception e
          (assert (= :idle (:fsm-state (ex-data e))))))))

(defn dispatch>!! [dispatch-v]
  (rf/dispatch dispatch-v)
  (pump-event-queue!))
