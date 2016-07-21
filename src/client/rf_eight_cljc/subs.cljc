(ns rf-eight-cljc.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/def-sub
  :name
  (fn [db _] (:name db)))

(re-frame/def-sub
  :seen
  (fn [db _] (:seen db)))
