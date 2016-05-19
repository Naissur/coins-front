(ns coins-front.core
  (:require 
    [reagent.core :as r]
    [coins-front.components.app :as app])) 

(enable-console-print!)


(r/render [app/app]
          (js/document.getElementById "app"))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
