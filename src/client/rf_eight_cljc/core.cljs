(ns rf-eight-cljc.core
  (:require [ajax.core :as ajax]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [devtools.core :as devtools]
            [rf-eight-cljc.handlers]
            [rf-eight-cljc.subs]
            [rf-eight-cljc.views :as views]))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(re-frame/def-fx
  :http
  (fn [{:keys [uri method payload on-success-v on-failure-v]}]
    (js/console.log "http payload: " payload)
    (let [ajax-fn (case method
                    :post   ajax/POST
                    :get    ajax/GET
                    :put    ajax/PUT
                    :delete ajax/DELETE)]
      (ajax-fn uri {:params          payload
                    :format          :json
                    :response-format :json
                    :handler         #(re-frame/dispatch (conj on-success-v %))
                    :error-handler   #(re-frame/dispatch (conj on-failure-v %))}))))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (devtools/install!)
  (mount-root))
