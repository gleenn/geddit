(ns geddit.events
  (:require [re-frame.core :as re-frame]
            [day8.re-frame.http-fx]
            [ajax.core]
            [geddit.db :as db]))

(defonce last-temp-id (atom 0))

(re-frame/reg-cofx
  :temp-id ;; same name
  (fn [cofx _]
    (assoc cofx :temp-id (swap! last-temp-id inc))))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-fx
  :create-dongle
  [(re-frame/inject-cofx :temp-id)]
  (fn [cofx [_ name]]
    {:http-xhrio {:uri (str "http://localhost:3449/dongles")
                  :method :post
                  :timeout 10000
                  :format (ajax.core/json-request-format)
                  :params {:name name}
                  :response-format (ajax.core/json-response-format {:keywords? true})
                  :on-success [:created-dongle (:temp-id cofx)]
                  :on-failure [:notified-error (:temp-id cofx)]}
     :db         (update-in (:db cofx) [:dongles] conj {:name name :id (:temp-id cofx)})}))

(re-frame/reg-event-fx
  :created-dongle
  (fn [cofx [_ id json-body]]
    (.log js/console (str "Created dongle! json: " json-body))))

(re-frame/reg-event-fx
  :notified-error
  (fn [cofx & stuff]
    (.log js/console (str "Error making some ajax happen bro - " stuff " -"))))
