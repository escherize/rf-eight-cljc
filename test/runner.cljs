(ns runner
  (:require [cljs.test :as cljs-test :include-macros true]
            [rf-eight-cljc.rf-test]))


(defn ^:export run-html-tests []
  (cljs-test/run-tests 'rf-eight-cljc.rf-test))
