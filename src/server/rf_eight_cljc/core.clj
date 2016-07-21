(ns rf-eight-cljc.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :as json]
            [byte-streams :as bs]))

(def seen (atom []))

(defroutes handler
  (POST "/namer" [] (fn [r]
                      (let [n (-> r :body bs/to-string (json/parse-string keyword) :name)]
                        (swap! seen conj n)
                        (json/encode {:count (count @seen)
                                      :seen @seen}))))
  (route/not-found "<h1>Page not found</h1>"))
