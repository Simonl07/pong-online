# pong-online
Multiplayer pong game using sockets connection and java applet/2D graphics

<img src="https://teachingkidstocode.io/wp-content/uploads/2017/08/Pong-1920x1200-31.jpg" width="384" height="240" />


The project breaks down into the following modules:

## Graphics Engine:
The graphics engine is used by game client and responsible for rendering the game component onto the screen, the engine is using java applet or java 2D graphics library.
* - [ ] Research Java 2D graphics library
* - [ ] Model/Component classes: Ball, Board, Game, Field, Prop, Items
* - [ ] Time(s/ms)-based frame update instead of performance-based frame update(for client synchronization)

## Physics Engine:
The Graphics Engine will be referring to the physics engine to consult physics interactions such as edge detection, reflection and vector calculation to properly render the game play. The Physics Engine includes: 
 
* - [ ] General Edge Detection
* - [ ] Edge Reflection
* - [ ] Vector calculation for board and ball interaction

## Match-making:
Both game client & server contains modules designed for match making. The module will be using a long socket connection which will also used for in-game communication once a game session is matched. The server side contains thread safe data structure that stores waiting players and match players based on latency. Once a succesful game session is matched, server will send the game session id, scores(starts with 0:0), initial conditions(x, y, dx, dy for ball), and beginning timestamp.

* - [ ] Server-side threadsafe data structure for storing and matching waiting players, include the ability for client to cancel the request while waiting
* - [ ] New request are dispatched and handled by a single thread in server-side workqueue
* - [ ] Client-side UI for match making, cancelling.
* - [ ] Server-side game tracking - initial pos, initial vector generation, score tracking, session tracking.


## In-game communication:
Both game client & server contains modules for in-game communication. These communication includes:

* - [ ] Rebounce Reports: new bounce vector report from client
* - [ ] Board Position Reports: current board position from client, necessary for real time opponent board rendering
* - [ ] Round End Reports: reports that the ball entered current players side and represents that the current player lose the round, send from client
* - [ ] New Round: signals a new round with initial conditions and scores. Send from server to clients. This also works for resumed sessions when a disconnected player reconnect.


## Game dynamics extension:

If we have time, it would be ideal to add more interesting game play into the vintage game. Possible additional gameplay feature:

* - [ ] In game items: accelerator, decelerator, obstacles, random vector, death trap, dual-ball and size changer.


# Notes:
#### On synchronizations:
A crucial design choice we have made for this game is that the ball position calculation/rendering will not be done on server and broadcase information to all client. Instead, game server is only responsible for match making, rebounce vector broadcast, and opponent board positions broadcast. 

The benefit of this design is that it is much more efficient and can makes the rendering of ball much smoother. It is efficient because as long as the state(items locations, ball: x, y, dx, dy) of both client is synchronized, only rebounce vector is needed to render the entire bounce back until next player bounce the ball again. This is because there are no other user-depended tragectory change after the rebounce. This way all rendering will be done simultaneously on both client in real time, increasing the framerate, and reduce large quatity of position broadcast.

However, this requires that the state of both client must be constantly synchronized. To solve the synchronization problem, we added Epoch timestamp on all communications. This allow the opponent, although may be few hundreds milliseconds delayed for receiving the rebounce report, still be able to calculate the real time position based on the given timestamp, current timestamp, and the rebounce vector.
