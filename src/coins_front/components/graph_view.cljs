(ns coins-front.components.graph-view
  (:require 
    [cljs.core :as core]
    [coins-front.math-utils :as math]
    [coins-front.date-utils :as date]
    [coins-front.event-utils :as events]))


(def width 600)
(def height 400)


(defn get-point-coords [width heigth start-date end-date start-x end-x color point]
  (let [t (:t point) 
        x (:x point)
        px (math/linear-transform start-date end-date 0 width t)
        py (math/linear-transform start-x end-x 0 height x)]
    {:x px :y py}))



(defn graph-point [width height start-date end-date start-x end-x color point]
  (let [{px :x py :y} (get-point-coords width height start-date end-date start-x end-x color point)
        t (:t point) ]
    [:circle {:key t 
              :cx px :cy py :r 3 
              :stroke-width 2 :stroke color :fill "#fff"}]))


(defn graph-line [width height start-date end-date start-x end-x color a b]
  (let [{ax :x ay :y} (get-point-coords width height start-date end-date start-x end-x color a) ta (:t a) 
        {bx :x by :y} (get-point-coords width height start-date end-date start-x end-x color b) tb (:t b)]

    [:line {:key (str ta "-" tb) 
            :x1 ax :y1 ay
            :x2 bx :y2 by
            :stroke color
            :stroke-width 2}]))


(defn line-plotter [width height start-date end-date start-x end-x color graph]
  (let [pairs (core/partition 2 1 graph)]
    (map (fn [[a b]] 
           (graph-line width height start-date end-date start-x end-x color a b)) pairs) ))

(defn points-plotter [width height start-date end-date start-x end-x color graph]
  (map (partial graph-point width height start-date end-date start-x end-x color) graph)) 

(defn graph-view [width height start-date end-date start-x end-x color 
                  on-mouse-down on-mouse-up on-mouse-move
                  graph]
  [:svg {:width width :height height 
         :on-mouse-down (comp on-mouse-down events/cast-mouse-ev)
         :on-mouse-up (comp on-mouse-up events/cast-mouse-ev)
         :on-mouse-move (comp on-mouse-move events/cast-mouse-ev)}

   [:rect {:x 0 :y 0 :width width :height height :stroke "#999" :fill "transparent"}] 
   (line-plotter 
     width height 
     start-date end-date
     start-x end-x
     color graph)
   (points-plotter 
     width height 
     start-date end-date
     start-x end-x
     color graph)])




