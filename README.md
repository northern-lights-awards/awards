# Yearly "Best country for watching Northern Lights" Awards

![](awards-template.jpg)
All reports since 2010 could be found [here](reports).

## Countries taking part in a rally for 2019 award

 - [Norway](data/countries/norway.json)
 - [Sweden](data/countries/sweden.json)
 - [Finland](data/countries/finland.json)
 - [Iceland](data/countries/iceland.json)
 
## Methodology

Each year [GFZ German Research Centre for GeoSciences](https://www.gfz-potsdam.de/en/home/) publishes the Aurora statistics through the year.
In the [yearly dataset](ftp://ftp.gfz-potsdam.de/pub/home/obs/kp-ap/wdc/) each record contains very important 3-hour Kp-index.
It describes the disturbance of the Earth's magnetic field caused by the solar wind.

### Dataset: montly/daily/hourly measures

So, the dataset contains 365(6) records per year.
Each record is a set of daily measures divided into 8 groups of 3-hour measures:

     - 0-3 range
     - 3-6 range
     - 6-9 range
     - 9-12 range
     - 12-15 range
     - 15-18 range
     - 18-21 range
     - 21-24 range

Each of 3-hour measure has its own kP value as well as aP value, more information on the dataset you may find [here](ftp://ftp.gfz-potsdam.de/pub/home/obs/kp-ap/wdc/wdc_fmt.txt).
The dataset can be effectively transformed into the following one:
```text
    {
        <date>: {
            <3-hour-range>: <kP-measure>
      }
    }
```
A new format of the dataset will allow effectively work daily measures (filtering 3-hour ranges with kP value lower than requested one, finding MAX etc.).

### Sun. Weather. Position. Season

An ability to witness the Aurora is the result of multiple factors:

    - Sun's activity that causes the disturbance
    - weather (humidity, cloud cover, temperature)
    - appropriate geo-positioning (meaning posted below)
    - the season of the year

#### Sun

The value of the disturbance value (kP) is pretty obvious - the greater disturbance causing higher chances to see the Aurora.
But ability to "see" is quite challenging, mostly due to the weather.

#### Weather

At most of the times, the highest impact is caused by the weather.
High and low clouds and the cloud cover in general, high humidity and higher temperature.
The Aurora looks perfect on photos and in person only when the sky is clear 
with pretty low temperature and low humidity along with nearly-zero cloud cover.

#### Positioning

But the weather is not the only factor that may impact chances to witness the Northern Lights.
First of all, at most of the times with a moderate sun's activity, it's highly recommended to go far north (as close as possible or far beyond the Polar Circle).

#### Season

The winter season is the best one for chasing the Aurora.
During winter months the length of the daytime is pretty short, up to 6 hours, or could even be shorter depending on a country's location.


## Theory declaration

This is the most important part of this project!

### Thesis

According to the magnetic field disturbance theory, the whole Polar Circle divided onto belts ranged from 0 to 9. 
Each belt has an assigned magnetic field disturbance coefficient of a 3-hour measure (known as kP).
Based on the minimum (minimum enough to see the aurora with the naked eye) kP value, for the particular country within the particular belt, 
for each observation point (defined as the GPS point) within the country, 
identify particular dates with recorder kP higher than the minimum kP taking into account weather conditions through the winter night hours (9PM to 6AM).


### Explanation

Taking into account that nobody collects reports of the aurora observation at ALL locations. The only data we could rely on is - GFZ data.
So, having a knowledge regarding minimal values for the disturbance that leads to potential visibility of the lights, 
it is necessary to figure out historical weather forecasts for the whole Aurora season.
With that, we could declare a simple rule:

    If solar wind will cause a great disturbance, with a constant good weather (short percentage of cloud cover, 
    low humidity, near-to-zero chances of perceptation), during the winter, at nighttime, 
    between 9PM to 6AM the chances to see the Northern Lights are pretty high.

In real life, with a historical weather forecasts it is highly possible to identify the number of days, the actual dates, 
exact locations where the Aurora was visible with naked eye.

FYI. In most of the northern european countries, winter months are: September to April. So, we'll be using these months to check if a good weather days matched Sun's activity.

### Algorithm

```
    1. For each country in the rally, filter out days my country's minimum kP during nighttime.
    2. For each of the GPS coordinates of a country, check nighttime hours weather of each date filtered at step 1.
    3. Using historical weather forecasts, identify whether the particular day was successful to witness the Aurora.
    4. Calculate the statistics: successful dates per GPS location, the over dates, total number of days with the kP value higher than country's minimal one.
```

Doesn't seem to be very complicated, does it? 

## Results

Aaaaaaand the winner is... okay, not yet (or scroll down).

Due to specialties of each country taking part in the rally. It is really hard to compare the experience, but it's easy to compare numbers.
As for the landscape photography, it is more important to have a descent foreground, rather than just the sky.

All locations used in this comparison are heavily inspired and tested by lots of landscape photographers.
So, the following number would mean more to the photographers rather than to typical tourists who doesn't care much of a photography rather than about sightseeing.


### 2019

#### Norway

I've been to northern Norway (Nordlands) in 2019, and I kinda can confirm these result.
Out of the 12 days of trip we've seen the Aurora maybe 3-4 times somewhere between 2nd to 9th of February.

```text
Norway had 27 days with observable northern lights, 45.76271186440678% of total dates
per-month activity (month to a number of dates with higher kP): {
	FEBRUARY : <[2019-02-21, 2019-02-09, 2019-02-05, 2019-02-01] days>, 
	OCTOBER : <[2019-10-31, 2019-10-28, 2019-10-27, 2019-10-02] days>, 
	NOVEMBER : <[2019-11-23, 2019-11-22, 2019-11-21, 2019-11-11, 2019-11-06] days>, 
	JANUARY : <[2019-01-31, 2019-01-25, 2019-01-24, 2019-01-23, 2019-01-14, 2019-01-05, 2019-01-04] days>, 
	MARCH : <[2019-03-17, 2019-03-16, 2019-03-08, 2019-03-07, 2019-03-03, 2019-03-02, 2019-03-01] days>
}
```

The winter season of 2019 was quite in Norway, mostly due to constantly changing weather. It was snowing a lot.
So, the sky remained covered with clouds at most of the time.


#### Iceland

Personally, I haven't been to Iceland in winter months yet. But things look better than in Norway.
However, it doesn't mean that spotting the Aurora is very easy.

```text
Iceland had 35 days with observable northern lights, 59.32203389830509% of total dates
per-month activity (month to a number of dates with higher kP): {
	NOVEMBER : <[2019-11-29, 2019-11-17, 2019-11-06] days>, 
	DECEMBER : <[2019-12-19, 2019-12-11, 2019-12-09] days>, 
	MARCH : <[2019-03-28, 2019-03-17, 2019-03-16, 2019-03-08, 2019-03-07] days>, 
	JANUARY : <[2019-01-31, 2019-01-25, 2019-01-23, 2019-01-17, 2019-01-16, 2019-01-15, 2019-01-06] days>, 
	OCTOBER : <[2019-10-31, 2019-10-29, 2019-10-28, 2019-10-27, 2019-10-26, 2019-10-10, 2019-10-02, 2019-10-01] days>, 
	FEBRUARY : <[2019-02-18, 2019-02-14, 2019-02-13, 2019-02-10, 2019-02-09, 2019-02-05, 2019-02-03, 2019-02-02, 2019-02-01] days>
}
```

35 days to 59 days overall, a pretty damn good stats. But take a closer look, it was required to stay long enough to see the Aurora.
1st week of February was CRAZY: Feb 1-3, 3-5, 5-10. It means that you'd need to stay for at least 2 weeks to rise your chances to the see Aurora.
But 2 weeks in Iceland is quite challenging, especially, if you're not doing the photography as the variety of activities is quite limited and the weather could be really challenging.

#### Sweden

Sweden is probably the southernmost country of those that take part in 2019 rally.
It means that Aurora must be very strong to be visible, even thought that lots of photographers do Northern Lights tours to Abisko.

```text
Sweden had 17 days with observable northern lights, 45.945945945945944% of total dates
per-month activity (month to a number of dates with higher kP): {
	DECEMBER : <[2019-12-19] days>, 
	NOVEMBER : <[2019-11-24, 2019-11-23] days>, 
	OCTOBER : <[2019-10-29, 2019-10-27] days>, 
	FEBRUARY : <[2019-02-21, 2019-02-14, 2019-02-05] days>, 
	MARCH : <[2019-03-31, 2019-03-17, 2019-03-16, 2019-03-01] days>, 
	JANUARY : <[2019-01-31, 2019-01-24, 2019-01-23, 2019-01-05, 2019-01-04] days>
}
```

In 2019, Sweden was not the best location to see the Aurora, if not to say - the worst. Only few days, on a long period of time.
Doesn't sound fun, at all.

#### Finland

I was quite surprised to see these numbers, really. I had a feeling that Nordlands is way more up north (Alta, Nordcap), but I was wrong.
I'm happy to be wrong, really!
First of all, I haven't been to Finland yet, but according to this research, Finnish Lapland is the best place to witness the Aurora.

```text
Finland had 59 days with observable northern lights, 100.0% of total dates
per-month activity (month to a number of dates with higher kP): {
	DECEMBER : <[2019-12-19, 2019-12-11, 2019-12-09] days>, 
	NOVEMBER : <[2019-11-29, 2019-11-24, 2019-11-23, 2019-11-22, 2019-11-21, 2019-11-17, 2019-11-11, 2019-11-06] days>, 
	MARCH : <[2019-03-31, 2019-03-28, 2019-03-17, 2019-03-16, 2019-03-15, 2019-03-14, 2019-03-08, 2019-03-07, 2019-03-03, 2019-03-02, 2019-03-01] days>, 
	JANUARY : <[2019-01-31, 2019-01-25, 2019-01-24, 2019-01-23, 2019-01-19, 2019-01-17, 2019-01-16, 2019-01-15, 2019-01-14, 2019-01-06, 2019-01-05, 2019-01-04] days>, 
	OCTOBER : <[2019-10-31, 2019-10-29, 2019-10-28, 2019-10-27, 2019-10-26, 2019-10-25, 2019-10-24, 2019-10-21, 2019-10-10, 2019-10-05, 2019-10-02, 2019-10-01] days>, 
	FEBRUARY : <[2019-02-28, 2019-02-27, 2019-02-21, 2019-02-18, 2019-02-14, 2019-02-13, 2019-02-10, 2019-02-09, 2019-02-08, 2019-02-05, 2019-02-03, 2019-02-02, 2019-02-01] days>
}
```
100% - WOW. This is impressing.


### Winner

It does seem to be obvious - the winner has nearly 100% match between good weather days and high magnetic field disturbance.

***FINLAND***

```text
And the Winner is: Finland, Congrats!
Chances to see the Aurora were: 100.0
With total number of clear sky nights vs high kP nights: 59 vs 59
The best month: FEBRUARY
The best location: Kilpisjärvi [69.0443388,20.803093]
```

## Further observations

First of all, I'm not trying to predict the the Sun's activity rather than to figure out if there's any pattern between years and countries.
The GFZ provides data since 1938, but the historical weather reports are available only for the last 10 years (GPS-based).
So, I'm kinda limited in a number of years to compare: 2010 to 2019.

According to the reports I've [calculated](reports), until 2018 both Iceland and Finland were sharing the No. 1 place as The Best Country for the Northern Lights.
However, as I stated above, Iceland is No.1 if you stay long enough, for at least 2 weeks as the Sun's activity and a good weather days are spread through months.
The same barely applies to Finland.

Until 2013, March was the best month to see the Lights in Iceland. Since than, the period of time has changed to sometime between October and December.

Since 2018, Finland became No.1 country for the Northern Lights. Maybe cause of the number of storms going through northern Atlantic, maybe climate changed?
But Finland is the mainland, which means the temperature is low, all clouds eventually turning themselves into snow showers, that leads to clear skies.