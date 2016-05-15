(ns coins-front.core
  (:require [reagent.core :as r])) 

(enable-console-print!)



(defonce app-state (r/atom {:count 0}))



(defn counterHeader [counter]
  [:h1 (str "Counter = " counter)])

(defn counterButton [onClick caption]
  [:button {:on-click onClick} caption])


(defn mapCounter [ func ]
  (let [curr (:count @app-state)]
    (reset! app-state {:count (func curr)})))

(defn incCounter [] (mapCounter inc))
(defn decCounter [] (mapCounter dec))


(defn app []
  [:div
   [counterHeader (:count @app-state)]
   [counterButton incCounter "Increase counter"]
   [counterButton decCounter "Decrease counter"] ])

(r/render [app]
          (js/document.getElementById "app"))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
