(ns coins-front.graph-utils)


(defn point-range-pos [start-date end-date point]
  (let [ [t] point]
    (if (< t start-date) :before
      (if (> t end-date) :after
        :in))))

(defn get-points-in-range  [start-date end-date points]
  (let
    [groups (group-by (partial point-range-pos start-date end-date) points)
     last-before (last (:before groups))
     first-after (first (:after groups))]

    (concat
      (if (nil? last-before) [] [last-before])
      (:in groups)
      (if (nil? first-after) [] [first-after]))))



(defn get-time-range [points]
  "points :: sorted-map"
  (let 
    [minimum (first (first points)) 
     maximum (first (last points))]

    {:start minimum :end maximum}))

(defn erase-time-range [start end points]
  (filter 
    (fn [point]
      (not (= :in (point-range-pos start end point) ))) points))

(defn merge-points [points-into points-what]
  (let [time-range (get-time-range points-what)]

    ((comp 
       (partial merge points-what)
       (partial erase-time-range (:start time-range) (:end time-range))) 
     points-into)))

