(ns rf-eight-cljc.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [rf-eight-cljc.rf-test]))

(doo-tests 'rf-eight-cljc.rf-test)
