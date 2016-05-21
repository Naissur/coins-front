(ns coins-front.components.graph-view
  (:require-macros [cljs.core.async.macros :refer  [go go-loop]])
  (:require 
    [reagent.core :as r]
    [cljs.core.async :refer [put! chan <! >! timeout close!]]
    [coins-front.test-graph :as tg]
    [coins-front.math-utils :refer [linear-transform]]
    [coins-front.event-utils :refer [is-mouse-move-ev is-mouse-down-ev is-wheel-ev ]]
    [coins-front.components.pure-graph-view :refer [pure-graph-view] ]))

(def initial-view-state 
  {
   :mouse-x 0
   :mouse-y 0
   :start-x 0
   :end-x 10
   :start-date tg/initial-date
   :end-date (+ tg/initial-date (* 7 tg/dt))})

(defonce view-state (r/atom initial-view-state))

(defn merge-view-state [to-merge]
  (reset! view-state 
          (merge @view-state to-merge)))





(defn get-zoom-deltas [width height initial-start-time initial-end-time mouse-x delta]
  (let [
        pointer-time (linear-transform 0 width initial-start-time initial-end-time mouse-x)
        time-span (- initial-end-time initial-start-time)

        rel-start (/ (- pointer-time initial-start-time) time-span)
        rel-end (/ (- initial-end-time pointer-time) time-span)

        dt (linear-transform 0 width 0 (- initial-end-time initial-start-time) delta) ]

    {:start-dt (* dt rel-start)
     :end-dt (* dt rel-end)}))





;; events go-routine


(def ev-chan (chan))
(defn on-mouse-ev [width height ev] (put! ev-chan {:width width :height height :ev ev}))


(defn go-drag [initial-start-time initial-end-time drag-start-x drag-start-y ev-chan]
  (go-loop []
           (let [{:keys [width height ev]} (<! ev-chan)]

             (if (is-mouse-move-ev ev)
               (do 
                 (let [dx (- (:x ev) drag-start-x)
                       dy (- (:y ev) drag-start-y)
                       time-span (- initial-end-time initial-start-time)
                       dt (linear-transform 0 width 0 time-span dx) ]

                   (merge-view-state {:start-date (- initial-start-time dt)
                                      :end-date (- initial-end-time dt)})
                                     (recur)))))))

(go-loop []
         (let [{:keys [width height ev]} (<! ev-chan)
               initial-start-time (:start-date @view-state) 
               initial-end-time (:end-date @view-state) ]

           (if (is-wheel-ev ev)
             (let 
               [{:keys [start-dt end-dt]} (get-zoom-deltas 
                                            width height 
                                            initial-start-time initial-end-time 
                                            (:mouse-x @view-state) (* 2 (:delta ev))) ]

               (merge-view-state {:start-date (- initial-start-time start-dt)
                                  :end-date (+ initial-end-time end-dt)})))

           (if (is-mouse-move-ev ev)
             (merge-view-state { :mouse-x (:x ev)
                                :mouse-y (:y ev)}))

           (if (is-mouse-down-ev ev)
             (<! (go-drag initial-start-time initial-end-time (:x ev) (:y ev) ev-chan)))

           (recur)))



(defn graph-view [params graph]
  (let 
    [{:keys [width height plot-type]} params
     {:keys [start-x end-x start-date end-date mouse-x mouse-y]} @view-state]

    [:div
     [pure-graph-view 
      {:width width :height height 
       :start-date start-date
       :end-date end-date
       :start-x start-x
       :end-x end-x
       :plot-type plot-type
       :on-mouse-ev (partial on-mouse-ev width height)}
      graph]

     [:p
      (str "x=" mouse-x " y=" mouse-y)]]))


