(ns coins-front.components.app
  (:require-macros [cljs.core.async.macros :refer  [go go-loop]])
  (:require 
    [cljs.core.async :refer [put! chan <! >! timeout close!]]
    [reagent.core :as r]
    [coins-front.components.graph-view :as graph]
    [coins-front.math-utils :refer [linear-transform]]
    [coins-front.test-graph :as tg]
    [coins-front.event-utils :as events]))


(def width 600)
(def height 400)
(def initial-state 
  {:graph tg/test-graph
   :start-x 0
   :end-x 10
   :start-date tg/initial-date
   :end-date (+ tg/initial-date (* 7 tg/dt))
   :color "green"})

(defonce state (r/atom initial-state))

;; (reset! state (assoc-in @state [:start-x] -10))

(def ev-chan (chan))

(defn on-mouse-up [e] (put! ev-chan e))
(defn on-mouse-down [e] (put! ev-chan e))
(defn on-mouse-move [e] (put! ev-chan e))


;; events go-routine

(defn go-drag [initial-start-time initial-end-time drag-start-x drag-start-y ev-chan]
  (go-loop []
           (let [ev (<! ev-chan)]

             (if (events/is-mouse-move-ev ev)
               (do 
                 (let [dx (- (:x ev) drag-start-x)
                       dy (- (:y ev) drag-start-y)

                       ;; dt = dx * (initial-end-time - initial-start-time) / width 

                       dt (* dx (/ (- initial-end-time initial-start-time) width) )]

                   (reset! state 
                       (assoc @state
                              :start-date (- initial-start-time dt)
                              :end-date (- initial-end-time dt)))
                       (recur)))))))

(go-loop []
         (let [ev (<! ev-chan)]

           (if (events/is-mouse-down-ev ev)
             (let [initial-start-time (:start-date @state)
                   initial-end-time (:end-date @state) ]

             (<! (go-drag initial-start-time initial-end-time (:x ev) (:y ev) ev-chan))))

           (recur)))


(defn app []
  (let [val @state]

    [:div
     [graph/graph-view 
      width height 
      (:start-date val) (:end-date val) 
      (:start-x val) (:end-x val) (:color val) 
      on-mouse-down on-mouse-up on-mouse-move
      (:graph val)]]))

