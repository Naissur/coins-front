(ns coins-front.test-graph)


(def initial-date 1463534536000)
(def dt (* 60 1000))                  ;; hour


(def portion-ranges 
  [(range 0 10 0.3)
   (range 3 6 0.1)])

(defn mapping-fn [t]
  [ (+ initial-date (* t dt)) (+ 1 (/ (* t t) 12)) ])


(defn get-portion-points [portion-range]
  ((comp 
     (partial apply sorted-map)
     flatten
     (partial map mapping-fn)) portion-range))




(def test-graph 
  {:points (apply merge (map get-portion-points portion-ranges))

   :color "orange" })

;;  [{:x 0 :t initial-date} 
;;   {:x 1 :t (+ initial-date (* 2 dt))}
;;   {:x 2 :t (+ initial-date (* 3 dt))}
;;   {:x 4 :t (+ initial-date (* 4 dt))}
;;   {:x 8 :t (+ initial-date (* 5 dt))}
;;   {:x 10 :t (+ initial-date (* 6 dt))}])
