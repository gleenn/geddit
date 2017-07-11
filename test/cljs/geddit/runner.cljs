(ns geddit.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [geddit.core-test]))

(doo-tests 'geddit.core-test)
