(defproject rf-eight-cljc "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.89"]
                 [reagent "0.6.0-rc"]
                 [binaryage/devtools "0.7.2"]
                 [re-frame "0.8.0-alpha2"]
                 [cljs-ajax "0.5.8"]
                 [compojure "1.5.1"]
                 [cheshire "5.6.3"]
                 [byte-streams "0.2.2"]
                 [lein-doo "0.1.7"]]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-doo "0.1.7"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]
             :nrepl-port 7889
             ;; Load CIDER, refactor-nrepl and piggieback middleware
             :nrepl-middleware ["cider.nrepl/cider-middleware"
                                "refactor-nrepl.middleware/wrap-refactor"
                                "cemerick.piggieback/wrap-cljs-repl"]
             :ring-handler rf-eight-cljc.core/handler}

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :profiles
  {:dev
   {:dependencies [[figwheel-sidecar "0.5.4-7"]
                   [com.cemerick/piggieback "0.2.1"]]

    :plugins [[lein-figwheel "0.5.4-7"]
              [cider/cider-nrepl "0.13.0-SNAPSHOT"]]
    }}

  :test-paths     ["test"]

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "rf-eight-cljc.core/mount-root"}
     :compiler     {:main                 rf-eight-cljc.core
                    :closure-defines      {goog.DEBUG true}
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            rf-eight-cljc.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}
    {:id           "test"
     :source-paths ["src/cljs" "test/cljs"]
     :compiler     {:output-to     "resources/public/js/compiled/test.js"
                    :main           rf-eight-cljc.runner
                    :optimizations :none}}

    ]}

  :aliases {"test-once"
            ["do" "clean," "cljsbuild" "once" "test," "shell" "open" "test/test.html"]}

  )
