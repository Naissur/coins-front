(ns coins-front.event-utils)

(def mouse-down "mousedown")
(def mouse-up "mouseup")
(def mouse-move "mousemove")


(defn is-ev-of-type [ev-type ev] (== (:type ev) ev-type))

(defn is-mouse-down-ev [ev] (is-ev-of-type mouse-down ev))
(defn is-mouse-up-ev [ev] (is-ev-of-type mouse-up ev))
(defn is-mouse-move-ev [ev] (is-ev-of-type mouse-move ev))


(defn cast-mouse-ev 
  [ev]
  {:type (.-type ev)
   :x (.-pageX ev)
   :y (.-pageY ev)})
