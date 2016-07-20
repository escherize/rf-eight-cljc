(ns rf-eight-cljc.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/def-sub
  :name
  (fn [db] (:name db)))

(re-frame/def-sub
  :seen
  (fn [db] (:seen db)))
