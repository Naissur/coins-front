(ns coins-front.core
  (:require 
    [reagent.core :as r]
    [coins-front.date-utils :as date-utils]
    [coins-front.components.graph-view :as graph])) 

(enable-console-print!)



(defn app []
  [:div
   [graph/graph-view []] ])

(r/render [app]
          (js/document.getElementById "app"))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
