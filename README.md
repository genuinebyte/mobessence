# MobEssence
Everything living has a life force, it's what makes them what they are. We call it essence, and if
we're able to extract it as they die, then it can be distilled and injected into an egg so they may
once again walk the earth.

### Attaining Essence
When a mob dies, it has a chance to drop its essence. This chance is configurable and defaults
to every 1 in 256 mobs.

### Infusing Essence into Eggs
A method of infusing chicken eggs with essence has come to light. It allows you to change the
genetics of the egg so that it will spawn the mob that had its essence infused.

In the center of your workbench you place the chicken egg. In the immediately surrounding squares
is where you place your essence. The last four spots left are the bone meal for the essence
to cultivate. You can see this below.

![workbench-recipe](docs/recipe.png)

## Configuration
If you think something that isn't currently configurable should be, then leave
an issue and I'll see if I can get it done.

**drop-chance**  
Default: `256`

The chance essence will drop from a mob. 1 of drop-chance mobs will drop essence. Four essence
are required to make an egg, so essentially an egg is attained 1 of (4 * drop-chance) mobs killed.

**require-player-kill**  
Default: `true`

Should the player have to be the cause of the mob's death for it to drop essence?