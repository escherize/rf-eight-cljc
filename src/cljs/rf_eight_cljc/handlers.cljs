(ns rf-eight-cljc.handlers
  (:require [re-frame.core :as re-frame]
            [rf-eight-cljc.db :as db]
            [ajax.core :refer [POST]]))

(re-frame/def-fx
  :http
  (fn [{:keys [payload]}]
    (js/console.log "http payload: " payload)
    (POST "/namer"
        {:params payload
         :format :json
         :response-format :json
         :handler (fn [{:strs [count seen]}]
                    (re-frame/dispatch [:update-name count])
                    (re-frame/dispatch [:reset-seen seen]))
         :error-handler #(re-frame/dispatch [:update-name (str "ERORR:" (pr-str %))])})))

(re-frame/def-event
  :initialize-db
  (fn  [_ _] db/default-db))

(re-frame/def-event
  :update-name
  [re-frame/debug re-frame/trim-v]
  (fn  [db [new-name]]
    (assoc db :name new-name)))

(re-frame/def-event
  :reset-seen
  (fn [db [_ seen]]
    (assoc db :seen seen)))

(re-frame/def-event-fx
  :submit-name
  re-frame/debug
  (fn [{:keys [db] :as world} _]
    {:db db
     :http {:payload db}}))
