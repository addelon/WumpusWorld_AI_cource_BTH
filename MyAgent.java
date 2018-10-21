package wumpusworld;
import java.util.*;



/**
 * Contains starting code for creating your own Wumpus World agent.
 * Currently the agent only make a random decision each turn.
 *
 * @author Johan Hagelbäck
 */
public class MyAgent implements Agent
{
    private World w;
    int rnd;

    boolean[][] stenchArray = new boolean [4][4];
    boolean[][] breezeArray = new boolean [4][4];
    float[][] probability = new float[4][4];
    int validSurrounding;
    int nrOfBreez;
    int nrOfStench;
    int nrOfVisitedCells;
    int goalX;
    int goalY;
    int wumpusX;
    int wumpusY;




    /**
     * Creates a new instance of your solver agent.
     *
     * @param world Current world state
     */
    public MyAgent(World world)
    {
        w = world;

        for(int row = 0; row < stenchArray.length; row++)
        {
            for(int column = 0; column < stenchArray[row].length; column++)
            {
                this.stenchArray[row][column] = false;
                this.breezeArray[row][column] = false;
                this.probability[row][column] = 1;
            }
        }
    }


    /**
     * Asks your solver agent to execute an action.
     */

    public void doAction()
    {
        //Location of the player
        int cX = w.getPlayerX();
        int cY = w.getPlayerY();


        if (w.hasGlitter(cX, cY))
        {
            w.doAction(World.A_GRAB);
            return;
        }


        if (w.isInPit())
        {
            w.doAction(World.A_CLIMB);
            return;
        }

         this.checkProb(cX, cY);
        //this.updateCells(cX, cY);
        this.findGoal();
        this.makeMove(cX, cY);

        this.showAllBoards();
        System.out.print("I am at: X:" + cX + " Y:" + cY + "\n");


    }
     public void findGoal()//findGoal
      {
          float min = 1.0f;
            for(int row = 1; row <= 4; row++)
            {
                for(int column = 1; column <= 4; column++)
                {
                    if(probability[row-1][column-1] < min && !w.isVisited(row, column))
                    {
                        min = probability[row-1][column-1];
                        goalX = row;
                        goalY = column;
                    }
                }
            }
            System.out.println("goalX: " + goalX + " goalY: " + goalY);
        }


       public double calcDistance(int x, int y)//distance
      {
          double dx = goalX - x;
          double dy = goalY - y;

          double distance = Math.sqrt(((dx*dx)+(dy*dy)));

          return distance;
      }

//      public void makeMove(int x, int y)//walk
//      {
//
//          //check shortest distance based on the goalX and goal Y
//          double[] alldistances = new double[4];
//
//          for(int i = 0;i<4;i++)
//          {
//              alldistances[i] = 100;
//          }
//
//        if(w.isValidPosition(x + 1, y))
//        {
//            if(w.isVisited(x + 1, y))
//            {
//               alldistances[0] = calcDistance(x + 1, y);
//            }
//        }
//
//        if(w.isValidPosition(x - 1, y))
//        {
//            if(w.isVisited(x -1, y))
//            {
//                alldistances[1] = calcDistance(x - 1, y);
//            }
//        }
//
//        if(w.isValidPosition(x, y + 1))
//        {
//            if(w.isVisited(x, y + 1))
//            {
//                alldistances[2] = calcDistance(x, y + 1);
//            }
//        }
//
//        if(w.isValidPosition(x, y - 1))
//        {
//            if(w.isVisited(x, y - 1))
//            {
//                alldistances[3] = calcDistance(x, y - 1);
//            }
//        }
//
//        double shortestDist = 5000;
//        int wantedDir = 0;
//
//        for(int i=0; i <4;i++)
//        {
//            if(shortestDist > alldistances[i])
//            {
//                 shortestDist = alldistances[i];
//                 wantedDir = i;
//            }
//        }
//
//         System.out.print("I want to go: " + wantedDir);
//
//        if(this.goalX - 1 == x && y == goalY)
//        {
//            if(w.getDirection()!=1)
//                w.doAction(World.A_TURN_RIGHT);
//            else
//            {
//                if(w.hasStench(x, y) && goalX == wumpusX && goalY == wumpusY)
//                {
//                    w.doAction(World.A_SHOOT);
//                }
//                w.doAction(World.A_MOVE);
//            }
//        }
//        else if(this.goalX + 1 == x && goalY == y)
//        {
//            if(w.getDirection() != 3)
//                w.doAction(World.A_TURN_LEFT);
//            else
//            {
//                if(w.hasStench(x, y) && goalX == wumpusX && goalY == wumpusY)
//                {
//                    w.doAction(World.A_SHOOT);
//                }
//                w.doAction(World.A_MOVE);
//            }
//
//        }
//        else if(goalX == x && goalY - 1 == y)
//        {
//            if(w.getDirection() != 0)
//                w.doAction(World.A_TURN_LEFT);
//            else
//            {
//                if(w.hasStench(x, y) && goalX == wumpusX && goalY == wumpusY)
//                {
//                    w.doAction(World.A_SHOOT);
//                }
//                w.doAction(World.A_MOVE);
//            }
//        }
//         else if(goalX == x && goalY + 1 == y)
//        {
//                if(w.getDirection() != 2)
//                {
//                    if(w.getDirection() > 2)
//                        w.doAction(World.A_TURN_LEFT);
//                    else if(w.getDirection() < 2)
//                        w.doAction(World.A_TURN_RIGHT);
//                }
//                else
//                {
//                    if(w.hasStench(x, y) && goalX == wumpusX && goalY == wumpusY)
//                    {
//                        w.doAction(World.A_SHOOT);
//                    }
//                    w.doAction(World.A_MOVE);
//                }
//
//        }
//        else
//         {
//           switch(wantedDir)
//            {
//                case 0:
//                    if(w.getDirection() != 1)
//                        w.doAction(World.A_TURN_RIGHT);
//                    else
//                        w.doAction(World.A_MOVE);
//                    break;
//                case 1:
//                    if(w.getDirection() != 3)
//                        w.doAction(World.A_TURN_LEFT);
//                    else
//                        w.doAction(World.A_MOVE);
//                    break;
//                case 2:
//                    if(w.getDirection() != 0)
//                        w.doAction(World.A_TURN_RIGHT);
//                    else
//                        w.doAction(World.A_MOVE);
//                    break;
//                case 3:
//                    if(w.getDirection() != 2)
//                        w.doAction(World.A_TURN_RIGHT);
//                    else
//                        w.doAction(World.A_MOVE);
//                    break;
//            }
//         }
//
//
//      }


       public void makeMove(int x, int y)//walk
      {

          //check shortest distance based on the goalX and goal Y
          double[] alldistances = new double[4];

          for(int i = 0;i<4;i++)
          {
              alldistances[i] = 100;
          }

        if(w.isValidPosition(x + 1, y))
        {
            if(w.isVisited(x + 1, y))
            {
               alldistances[0] = calcDistance(x + 1, y);
            }
        }

        if(w.isValidPosition(x - 1, y))
        {
            if(w.isVisited(x -1, y))
            {
                alldistances[1] = calcDistance(x - 1, y);
            }
        }

        if(w.isValidPosition(x, y + 1))
        {
            if(w.isVisited(x, y + 1))
            {
                alldistances[2] = calcDistance(x, y + 1);
            }
        }

        if(w.isValidPosition(x, y - 1))
        {
            if(w.isVisited(x, y - 1))
            {
                alldistances[3] = calcDistance(x, y - 1);
            }
        }

        double shortestDist = 5000;
        int wantedDir = 0;

        for(int i=0; i <4;i++)
        {
            System.out.print("DIST AT: " + i + " is " + alldistances[i] + "\n");
            if(shortestDist > alldistances[i])
            {
                 shortestDist = alldistances[i];
                 wantedDir = i;
            }
        }

         System.out.print("I want to go: " + wantedDir + "\n");

        if(this.goalX - 1 == x && y == goalY)
        {
            System.out.print("GoalCell is to my RIGHT\n");
            if(w.getDirection()!=1)
                w.doAction(World.A_TURN_RIGHT);
            else
            {
                if(w.hasStench(x, y) && goalX == wumpusX && goalY == wumpusY)
                {
                    System.out.print("WUMPUS IS TO MY RIGHT");
                    w.doAction(World.A_SHOOT);
                }
                w.doAction(World.A_MOVE);
            }
        }
        else if(this.goalX + 1 == x && goalY == y)
        {
            System.out.print("GoalCell is to my LEFT\n");
            if(w.getDirection() != 3)
                w.doAction(World.A_TURN_LEFT);
            else
            {
                if(w.hasStench(x, y) && goalX == wumpusX && goalY == wumpusY)
                {
                    System.out.print("WUMPUS IS TO MY LEFT");
                    w.doAction(World.A_SHOOT);
                }
                w.doAction(World.A_MOVE);
            }

        }
        else if(goalX == x && goalY - 1 == y)
        {
            System.out.print("GoalCell is UP\n");
            if(w.getDirection() != 0)
                w.doAction(World.A_TURN_LEFT);
            else
            {
                if(w.hasStench(x, y) && goalX == wumpusX && goalY == wumpusY)
                {
                    System.out.print("WUMPUS IS UP");
                    w.doAction(World.A_SHOOT);
                }
                w.doAction(World.A_MOVE);
            }
        }
         else if(goalX == x && goalY + 1 == y)
        {
            System.out.print("GoalCell is DOWN\n");
                if(w.getDirection() != 2)
                {
                    if(w.getDirection() > 2)
                        w.doAction(World.A_TURN_LEFT);
                    else if(w.getDirection() < 2)
                        w.doAction(World.A_TURN_RIGHT);
                }
                else
                {
                    if(w.hasStench(x, y) && goalX == wumpusX && goalY == wumpusY)
                    {
                        System.out.print("WUMPUS IS DOWN");
                        w.doAction(World.A_SHOOT);
                    }
                    w.doAction(World.A_MOVE);
                }

        }
        else
         {
             System.out.print("Last else sats: " + wantedDir);
           switch(wantedDir)
            {
                case 0:
                    if(w.getDirection() != 1)
                        w.doAction(World.A_TURN_RIGHT);
                    else
                        w.doAction(World.A_MOVE);
                    break;
                case 1:
                    if(w.getDirection() != 3)
                        w.doAction(World.A_TURN_LEFT);
                    else
                        w.doAction(World.A_MOVE);
                    break;
                case 2:
                    if(w.getDirection() != 0)
                        w.doAction(World.A_TURN_RIGHT);
                    else
                        w.doAction(World.A_MOVE);
                    break;
                case 3:
                    if(w.getDirection() != 2)
                        w.doAction(World.A_TURN_RIGHT);
                    else
                        w.doAction(World.A_MOVE);
                    break;
            }
         }


      }


       public boolean countAround(int x, int y)//countPos
     {
         boolean valid = false;
         if(w.isValidPosition(x, y))
         {
            this.validSurrounding++;

            if(w.isVisited(x, y))
            {
                this.nrOfVisitedCells++;

                if(w.hasBreeze(x, y))
                {
                    this.nrOfBreez++;
                }
                if(w.hasStench(x, y))
                {
                    this.nrOfStench++;
                }
                valid = true;
            }
         }
         return valid;
     }


        public float enterPros()//setPercent
     {
         float precentage = 0;

        switch (this.validSurrounding) {
            case 2:
                precentage = 0.5f;
                break;
            case 3:
                precentage = 0.33f;
                break;
            case 4:
                precentage = 0.25f;
                break;
            default:
                break;
        }

         return precentage;
     }


        public void checkAround(int x, int y, int startX, int startY)
     {
         this.nrOfBreez = 0;
         this.nrOfStench = 0;
         this.validSurrounding = 0;
         this.nrOfVisitedCells = 0;
         Boolean[] temp = new Boolean[4];
         //kollar grannarna.. inclusive cellen man står på.
         temp[0] = this.countAround(x + 1, y);
         temp[1] = this.countAround(x - 1, y);
         temp[2] = this.countAround(x, y + 1);
         temp[3] = this.countAround(x, y - 1);



         if(this.nrOfVisitedCells == nrOfStench)
        {
                wumpusX = x;
                wumpusY = y;
        }
         //kolla om breeze/stench stämmer överens med resten av infon för att säkerhetställa vart faran kan vara.
         if(this.nrOfVisitedCells != this.nrOfBreez && this.nrOfVisitedCells != nrOfStench)
        {
            probability[x-1][y-1] = 0;

        }
         else if(this.nrOfVisitedCells == this.nrOfBreez || this.nrOfVisitedCells == this.nrOfStench)
         {
             for(int i = 0; i < 4; i++)
            {
               if(temp[i] == true)
               {
                   switch(i)
                   {
                       case 0:
                            this.setPercentage(x, y, startX, startY);
                            break;
                       case 1:
                            this.setPercentage(x, y, startX, startY);
                            break;
                       case 2:
                            this.setPercentage(x, y, startX, startY);
                            break;
                       case 3:
                            this.setPercentage(x, y, startX, startY);
                            break;
                   }
               }
            }
         }
         else
         {

         }


     }


        public void setPercentage(int x, int y, int startX, int startY)//setProb
      {
          float percantage = enterPros();

        if(w.hasBreeze(startX, startY))
            probability[x-1][y-1] = percantage * this.nrOfBreez;

        if(w.hasStench(startX, startY))
            probability[x-1][y-1] = percantage * this.nrOfStench;
      }
        
        //Testar GIt
        //Testar ännu mera!!!!!
        //sebbe testar git
        public void checkProb(int x, int y)
    {
        this.probability[x-1][y-1]= 0;

        if(w.isValidPosition(x + 1, y))
        {
            if(!w.isVisited(x + 1, y))
            {
                checkAround(x + 1, y, x, y);
            }
        }

        if(w.isValidPosition(x - 1, y))
        {
            if(!w.isVisited(x - 1, y))
            {
                checkAround(x - 1, y, x, y);
            }
        }

        if(w.isValidPosition(x, y + 1))
        {
            if(!w.isVisited(x, y + 1))
            {
                checkAround(x, y + 1, x, y);
            }
        }

        if(w.isValidPosition(x, y - 1))
        {
            if(!w.isVisited((x), y - 1))
            {
                checkAround(x , y - 1, x, y);
            }
        }

    }





    public void showAllBoards()
    {

        System.out.print("PROBABILITY");
        System.out.print("\n");
        for(int row = 3; row >= 0; row--)
        {
            for(int column = 0; column < 4; column++)
            {
                System.out.print(this.probability[column][row]);
                System.out.print("\t");
            }
            System.out.print("\n");
        }



    }

     //kollar vilken precenteage det ska öka med..beroende på hur ånga valid rutor den har ex 1/4 = 25% 1/2 = 50%


     //kollar hur många validdrag runtom samt hur många breez/stench runtomkring



      //kolla grannceller om de är valid och visited för att sedan kolla deras grannar och sedan uppdatera probablity korrekt



      public void turnAndWalk(int myDir, int wantedDir)
      {
          //upp
        if (myDir==0)
        {
            if(wantedDir == 1)
            {
                w.doAction(World.A_TURN_RIGHT);

            }
            if(wantedDir == 2)
            {
                w.doAction(w.A_TURN_LEFT);
                w.doAction(w.A_TURN_LEFT);

            }
            if(wantedDir == 3)
            {
                w.doAction(World.A_TURN_LEFT);
            }

            w.doAction(World.A_MOVE);


        }

        //RIGHT
        if (myDir==1)
        {

            if(wantedDir == 0)
            {
                w.doAction(World.A_TURN_LEFT);
            }

            if(wantedDir == 2)
            {
                w.doAction(w.A_TURN_RIGHT);

            }
            if(w.getDirection() == 3)
            {
                w.doAction(World.A_TURN_RIGHT);
                w.doAction(World.A_TURN_RIGHT);
            }

            w.doAction(World.A_MOVE);
        }

        //DOWN
        if (myDir==2)
        {

            if(w.getDirection() == 0)
            {
                w.doAction(World.A_TURN_RIGHT);
                w.doAction(World.A_TURN_RIGHT);
            }
            if(w.getDirection() == 1)
            {
                w.doAction(World.A_TURN_LEFT);
            }

            if(w.getDirection() == 3)
            {
                w.doAction(World.A_TURN_RIGHT);

            }


            w.doAction(World.A_MOVE);
        }

        //LEFT
        if (myDir==3)
        {

            if(wantedDir == 0)
            {
                w.doAction(World.A_TURN_RIGHT);

            }
            if(wantedDir == 1)
            {
                w.doAction(World.A_TURN_RIGHT);
                w.doAction(World.A_TURN_RIGHT);
            }
            if(wantedDir == 2)
            {
                w.doAction(World.A_TURN_LEFT);

            }


            w.doAction(World.A_MOVE);
        }

      }
}
