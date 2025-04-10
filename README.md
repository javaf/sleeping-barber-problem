There is a barber sleeping in his shop. When a
customer comes, he checks if the barber is
sleeping and wakes him up. If there are no other
customers in the waiting room, the barber cuts
his hair. Else the customer takes a set in the
waiting room. But if there are no seats, he
leaves. Once a barber finishes cutting hair, he
checks if there are any customers in the waiting
room. If not, he goes back to sleep again. The
idea is the barber works only when a customer
arrives, and sleeps otherwise.

> **Course**: [Concurrent Data Structures], Monsoon 2020\
> **Taught by**: Prof. Govindarajulu Regeti

[Concurrent Data Structures]: https://github.com/iiithf/concurrent-data-structures

```java
The barber is providing 24x7 service:
1. Sleeps until a customer wakes him up
2. He picks first customer in room (b.release)
3. One seat is freed up (seats++)
4. Cuts customer's hair (sleep 1s)
... Goes back to sleep
```

```java
Each customer requires a haircut:
1. Occupies a seat, if available
2. Wakes up the barber (c.release)
3. Waits for barber to indicate his turn
... His hair is cut.
```

```bash
## OUTPUT
Starting barber (B) with 2 seats and 5 customers ...
B: sleeping
0: checking seats
B: got customer
B: cutting hair
0: sat, seats=1
0: having hair cut
1: checking seats
1: sat, seats=1
2: checking seats
2: sat, seats=0
3: checking seats
3: no seats!
B: cutting done
B: sleeping
B: got customer
B: cutting hair
1: having hair cut
4: checking seats
4: sat, seats=0
B: cutting done
B: sleeping
B: got customer
B: cutting hair
2: having hair cut
B: cutting done
B: sleeping
B: got customer
B: cutting hair
4: having hair cut
B: cutting done
B: sleeping
```

See [Main.java] for code, and [repl.it] for output.

[Main.java]: https://repl.it/@wolfram77/sleeping-barber-problem#Main.java
[repl.it]: https://sleeping-barber-problem.wolfram77.repl.run


### references

- [Sleeping Barber Problem : Classical Problem of Process Synchronization :: HowTo](https://www.youtube.com/watch?v=OvJFpsN5czg)

![](https://ga-beacon.deno.dev/G-G1E8HNDZYY:v51jklKGTLmC3LAZ4rJbIQ/github.com/javaf/sleeping-barber-problem)
![](https://ga-beacon.deno.dev/G-G1E8HNDZYY:v51jklKGTLmC3LAZ4rJbIQ/github.com/moocf/sleeping-barber-problem.java)
