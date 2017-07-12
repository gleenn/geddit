(ns geddit.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [response resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defroutes routes
           (GET "/" [] (resource-response "index.html" {:root "public"}))
           (GET "/news" [] (response "yo what's up"))
           (resources "/"))

(def dev-handler (-> #'routes wrap-reload))

(def handler routes)
