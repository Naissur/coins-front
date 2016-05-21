(ns coins-front.components.app
  (:require-macros [cljs.core.async.macros :refer  [go go-loop]])
  (:require 
    [cljs.core.async :refer [put! chan <! >! timeout close!]]
    [reagent.core :as r]
    [coins-front.components.graph-view :refer [graph-view]]
    [coins-front.math-utils :refer [linear-transform]]
    [coins-front.test-graph :as tg]
    [coins-front.event-utils :as events]))


(def width 800)
(def height 400)
(def initial-state 
  {:graph tg/test-graph :color "green"})

(set! (.-graph js/window) tg/test-graph)

(defonce state (r/atom initial-state))


(defn app []
  (let [val @state]

    [:div
     [graph-view
      {:width width :height height 
       :graph-color (:color val) 
       :graph-type :step}
      (:graph val)]]))

