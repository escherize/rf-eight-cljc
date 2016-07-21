(ns rf-eight-cljc.handlers
  (:require [re-frame.core :as re-frame]
            [rf-eight-cljc.db :as db]))

(re-frame/def-event
  :initialize-db
  (fn [_ _] db/default-db))

(re-frame/def-event
  :update-name
  [re-frame/debug re-frame/trim-v]
  (fn [db [new-name]]
    (assoc db :name new-name)))

(re-frame/def-event
  :reset-seen
  (fn [db [_ seen]]
    (assoc db :seen seen)))

(re-frame/def-event
  :http-success-received
  (fn [db [_ {:strs [count seen]}]]
    (re-frame/dispatch [:update-name count])
    (re-frame/dispatch [:reset-seen seen])
    db))

(re-frame/def-event
  :http-failure-received
  (fn [db [_ payload]]
    (re-frame/dispatch [:update-name (str "ERROR:" (pr-str payload))])
    db))

;; Output of submit-name will make :name = the count of 'seen',
;; and add the submitted-name into 'seen'.

(re-frame/def-event-fx
  :submit-name
  re-frame/debug
  (fn [{:keys [db] :as world} _]
    {:db   db
     :http {:uri          "/namer"
            :method       :post
            :payload      db
            :on-success-v [:http-success-received]
            :on-failure-v [:http-failure-received]}}))
