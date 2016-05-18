(ns coins-front.components.graph-view
  (:require 
    [coins-front.math-utils :as math]
    [coins-front.date-utils :as date]
    [coins-front.test-graph :as tg]))


(def width 600)
(def height 400)


(defn graph-point [width height color start-date end-date point]
  (let [t (:t point) 
        x (:x point)
        cx (math/linear-transform start-date end-date 0 width t)
        cy 2]
    [:circle {:cx cx :cy cy :r 2 :fill color}]))


(defn points-plotter [width height color start-date end-date graph]
  (map (partial graph-point width height color start-date end-date) graph)) 

(defn graph-view []
  [:svg {:width width :height height}
   [:rect {:x 0 :y 0 :width width :height height :stroke "#999" :fill "transparent"}] 
   (points-plotter width height "green" tg/initial-date (+ tg/initial-date (* 6 tg/dt)) tg/test-graph) ])



