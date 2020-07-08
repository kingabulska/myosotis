<h1 align="justify"> Myosotis </h1>

#### Mobile app for monitoring menstruation cycle based on temperature measurement. User add temperature value and type of cervical mucus everyday. Application storages data, draws cycle chart and determines posibility of pregnancy.

Application storages user data like username, cycle length, period length. User can change this values anytime in
Edit section. In Add section user adds morning's temperature and type of cervical mucus (one of five: lack, watery,
very watery, dense and very dense) everyday. After parameters saved user can overwrite values if they are wrong.
Application prevents accidental overwriting by dialog box with question "Do you want to overwrite temperature?".
All temperatures in one menstrual cycle are present in temperature's chart. Click on chart point to display point's
parameters such as cycle day, temperture, muscus, fertility/non-fertility day, possibility of pregnancy and period
day if period occurs. Algorithm for fertility and pregnancy calculation is mainly based on temperature value
but also takes mucus and cycle day values. Application allows history view.

Tech stack:
* Kotlin
* Room
* MPAndroidChart

![splashscreen] ![start]
![add] ![chart]
![toast] ![edit]

[add]: https://github.com/kingabulska/myosotis/blob/master/img/add.PNG
[edit]: https://github.com/kingabulska/myosotis/blob/master/img/edit.PNG
[chart]: https://github.com/kingabulska/myosotis/blob/master/img/chart.PNG
[start]: https://github.com/kingabulska/myosotis/blob/master/img/start.PNG
[toast]: https://github.com/kingabulska/myosotis/blob/master/img/toast.PNG
[splashscreen]: https://github.com/kingabulska/myosotis/blob/master/img/splash%20sceen.PNG

