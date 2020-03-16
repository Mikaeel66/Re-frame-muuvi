(ns kehys.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::name
 (fn [db]
   (:name db)))

(rf/reg-sub
    ::val
    (fn [db]
      (:val db)))
(rf/reg-sub
          ::cate
          (fn [db]
            (:cate db)))

(rf/reg-sub
    ::categories
    (fn [db]
      (:categories db)))

(rf/reg-sub
    ::muuvi
    (fn [db]
      (:muuvi db)))

