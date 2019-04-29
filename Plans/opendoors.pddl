; The levels 00 to 08 in problem files are taken from and can be tried at:
; https://armorgames.com/play/3357/open-doors-2

(define (domain OPENDOORS)
  ; typing is not used, as all objects in problem files refer to locations
  (:requirements :strips)

  ; the listed predicates occur in problem files,
  ; and please add any further predicates by need
  (:predicates ; (free ?l1 ?l2): locations ?l1 and ?l2 have no door in-between,
               ;                 and it is always possible to go from ?l1 to ?l2
               (free ?l1 ?l2)
               ; (pass ?l1 ?l2): locations ?l1 and ?l2 have a door in-between,
               ;                 and one can go from ?l1 to ?l2 if:
               ;                 - ?l2 includes the angle of the door; or
               ;                 - ?l1 includes the angle of the door and
               ;                   the door is open towards ?l2
               (pass ?l1 ?l2)
               ; (door ?l1 ?l ?l2): there is a door with angle at location ?l,
               ;                    and ?l1 and ?l2 are locations towards which
               ;                    the door can be open or closed
               (door ?l1 ?l ?l2)
               ; (open ?l1 ?l ?l2): the door with angle at location ?l is open
               ;                    towards ?l2 (and closed towards ?l1)
               (open ?l1 ?l ?l2)
               ; (gate ?l1 ?l2): locations ?l1 and ?l2 have a gate in-between,
               ;                 and one can:
               ;                 - open the gate by going from ?l1 to ?l2; or
               ;                 - close the gate by going from ?l2 to ?l1 if
               ;                   the gate is open
               (gate ?l1 ?l2)
               ; (at ?l): location ?l is the current position
               (at ?l)
  )




  ;agent opens a closed door and walks through it
  (:action open_door
    :parameters (?l1 ?l ?l2)
    :precondition (and
                    (open ?l1 ?l ?l2)
                    (at ?l1)
                  )
    :effect (and
              (open ?l2 ?l ?l1)
              (not (open ?l1 ?l ?l2))
              (at ?l)
              (not (at ?l1))
            )
  )

  ;agent walks over the door angle to change the position of the door
  (:action walk_over_door_angle_from_inside
    :parameters (?l1 ?l ?l2)
    :precondition (and
                    (at ?l)
                    (door ?l1 ?l ?l2)
                    (pass ?l ?l2)
                    (open ?l1 ?l ?l2)
     )
    :effect (and
              (at ?l2)
              (not (at ?l))
              (open ?l2 ?l ?l1)
              (not (open ?l1 ?l ?l2))
            )
  )

  ;agent walks to field which is is already open
  (:action walk_over_open_door
    :parameters (?l1 ?l ?l2)
    :precondition (and
                    (at ?l2)
                    (door ?l1 ?l ?l2)
                    (pass ?l ?l2)
                    (open ?l1 ?l ?l2)
                  )
    :effect (and
              (at ?l)
              (not (at ?l2))
            )
  )

  ;agent walks through a gate (gate is than open)
  (:action open_gate
    :parameters (?from ?to)
    :precondition (and
                    (at ?from)
                    (gate ?from ?to)
                  )
    :effect (and
              (at ?to)
              (not (at ?from))
              (free ?to ?from)
            )
  )

  ;agent walks through open gate from the inside and closes it
  (:action close_gate
    :parameters (?from ?to)
    :precondition (and
                    (at ?from)
                    (gate ?to ?from)
                    (free ?from ?to)
                  )
    :effect (and
              (at ?to)
              (not (at ?from))
              (not (free ?from ?to))
            )
  )


  ;agent walks from one field to another (no obstacles in between) 
  (:action walk_free
    :parameters (?from ?to)
    :precondition (and
                       ;player is at from
                       (at ?from)
                       ;he can freely walk
                       (free ?from ?to)
                       (not (gate ?to ?from))
                  )
    :effect (and (at ?to) (not (at ?from)))
  )






)
