(ns coins-front.event-utils)

(def mouse-down "mousedown")
(def mouse-up "mouseup")
(def mouse-move "mousemove")

(def wheel "wheel")


(defn is-ev-of-type [ev-type ev] (== (:type ev) ev-type))

(defn is-mouse-down-ev [ev] (is-ev-of-type mouse-down ev))
(defn is-mouse-up-ev [ev] (is-ev-of-type mouse-up ev))
(defn is-mouse-move-ev [ev] (is-ev-of-type mouse-move ev))
(defn is-wheel-ev [ev] (is-ev-of-type wheel ev))



(def mouse-ev-types #{mouse-down mouse-up mouse-move})

(defn is-nat-ev-mouse [ev]
  (contains? mouse-ev-types (.-type ev)))

(defn is-nat-ev-wheel [ev]
  (= (.-type ev) wheel))

(defn cast-mouse-ev [ev]
  {:type (.-type ev)
   :x (.-pageX ev)
   :y (.-pageY ev)})

(defn cast-wheel-ev [ev]
  {:type (.-type ev)
   :delta (.-deltaY ev)})


(defn cast-ev 
  [ev]
   (if (is-nat-ev-mouse ev) (cast-mouse-ev ev)
     (if (is-nat-ev-wheel ev) (cast-wheel-ev ev))))
