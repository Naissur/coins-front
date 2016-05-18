(ns coins-front.math-utils)

;; x' = c + (x - a) / (b - a) * (d - c)
(defn linear-transform 
  [a b c d x]

  (let [rel (/ (- x a) (- b a))]
    (+ c (* rel (- d c) ))))

