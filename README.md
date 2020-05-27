# MobEssence
[todo.sr.ht][tickets]

[tickets]: https://todo.sr.ht/~genbyte/mobessence

Everything living has a life force, it's what makes them what they are. We call it essence, and if
we're able to extract it as they die, then it can be distilled and injected into an egg so they may
once again walk the earth.

### Attaining Essence
When a mob dies, it has a chance to drop its essence. This chance is configurable and defaults
to every 1 in 1024 mobs.

### Infusing Essenec into Eggs
A method of infusing chicken eggs with essence has come to light. In the center of your workbench,
you place the chicken egg. In the immediatly surrounding squares is where you place your essence.
The last four spots left are for the bone meal for the essence to cultivate. You can see this below.

![workbench-recipe](docs/recipe.png)

### Configuration
There are two configuration values, `smelt-time` and `drop-chance`. They control how long it takes
to distill essence, and how often it drops, respectively. 

`smelt-time` is given in seconds and defaults to `3600`.

`drop-chance` is the inverse of the value you provie. So if you change it to 100 then there is a 
1 in 100 chance a mob will drop its essence on death. This value defaults to `1024`.

You can view the default configuration [here](src/main/resources/config.yml).