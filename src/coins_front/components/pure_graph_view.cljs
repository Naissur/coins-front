(ns coins-front.components.pure-graph-view
  (:require 
    [cljs.core :refer [partition]]
    [coins-front.math-utils :as math]
    [coins-front.date-utils :as date]
    [coins-front.event-utils :as events]))



(defn graph-point [params point]
  (let [{:keys [color]} params
        {px :x py :y react-key :key} point]

    [:circle {:key react-key
              :cx px :cy py :r 3 
              :stroke-width 2 :stroke color :fill "#fff"}]))


(defn graph-line [params a b]
  (let [
        {:keys [color]} params
        {ax :x ay :y a-react-key :key} a
        {bx :x by :y b-react-key :key} b]

    [:line {:key (str a-react-key "_" b-react-key)
            :x1 ax :y1 ay
            :x2 bx :y2 by
            :stroke color
            :stroke-width 2}]))


(defn line-plotter [params points]
  (let [{:keys [color]} params
        pairs (partition 2 1 points)]

    (map (fn [[a b]]
           (graph-line {:color color} a b)) pairs)))


(defn points-plotter [params points]
  (let [{:keys [color]} params]
    (map (partial graph-point {:color color}) points)))


(defn get-point [width height start-date end-date start-x end-x color point]
  (let [t (:t point) 
        x (:x point)
        px (math/linear-transform start-date end-date 0 width t)
        py (math/linear-transform start-x end-x 0 height x)]
    {:x px :y py :key t})) 

(defn pure-graph-view [params graph]
  (let [{:keys 
         [width height
          start-date end-date
          start-x end-x
          color 
          on-mouse-ev]} params

        points (map (partial get-point width height start-date end-date start-x end-x color) graph)
        handle-mouse-ev (comp on-mouse-ev events/cast-mouse-ev)]


    [:svg {:width width :height height 
           :on-mouse-down handle-mouse-ev
           :on-mouse-up handle-mouse-ev
           :on-mouse-move handle-mouse-ev}

     [:rect {:x 0 :y 0 :width width :height height :stroke "#999" :fill "transparent"}] 

     (line-plotter {:color color} points)
     (points-plotter {:color color} points) ]))



