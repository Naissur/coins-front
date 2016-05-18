(ns coins-front.core
  (:require 
    [reagent.core :as r]
    [coins-front.date-utils :as date-utils]
    [coins-front.components.graph-view :as graph])) 

(enable-console-print!)


;; (defonce app-state (r/atom {:count 0}))
;;
;;
;;      (defn counterHeader [counter]
;;        [:h1 (str "Counter is :" counter)])
;;      
;;      (defn counterButton [onClick caption]
;;        [:button {:on-click onClick} caption])
;;      
;;      
;;      (defn mapCounter [ func ]
;;        (let [curr (:count @app-state)]
;;          (reset! app-state {:count (func curr)})))
;;      
;;      (defn incCounter [] (mapCounter inc))
;;      (defn decCounter [] (mapCounter dec))
;;
;;
;;      (defn app []
;;        [:div
;;         [counterHeader (:count @app-state)]
;;         [counterButton incCounter "Increase counter"]
;;         [counterButton decCounter "Decrease counter"]
;;         [:div
;;          [:svg {:width 100 :height 100} 
;;           [:rect {:x 50 :y 50 :width 50 :height 50 :fill "#bbb"}]
;;           [:rect {:x 0 :y 0 :width 50 :height 50 :fill "#bbb"}]]]])


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
