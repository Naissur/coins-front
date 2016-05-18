(ns coins-front.test-graph)


(def initial-date 1463534536000)
(def dt (* 60 1000))                  ;; hour

(def test-graph 
  [{:x 0 :t initial-date} 
   {:x 1 :t (+ initial-date (* 2 dt))}
   {:x 2 :t (+ initial-date (* 3 dt))}
   {:x 4 :t (+ initial-date (* 4 dt))}
   {:x 6 :t (+ initial-date (* 5 dt))}
   {:x 8 :t (+ initial-date (* 6 dt))}])
