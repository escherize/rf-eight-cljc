(ns rf-eight-cljc.handlers
    (:require [re-frame.core :as re-frame]
              [rf-eight-cljc.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))
