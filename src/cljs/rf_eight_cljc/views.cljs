(ns rf-eight-cljc.views
  (:require [re-frame.core :as re-frame]))

(defn main-panel []
  (let [name (re-frame/subscribe [:name])
        seen (re-frame/subscribe [:seen])]
    (fn []
      [:div
       [:div "name = " [:span {:style {:font-style "monospace"}} @name]]
       [:input
        {:type "text"
         :on-change #(re-frame/dispatch
                      [:update-name (-> % .-target .-value)])
         :value @name}]
       [:div
        [:button
         {:type "button"
          :on-click #(re-frame/dispatch [:submit-name])}
         "Submit"]]
       (into [:ol] (for [s @seen] [:li s]))])))
