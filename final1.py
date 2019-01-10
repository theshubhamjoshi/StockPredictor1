#!/usr/bin/env python

import warnings

import itertools


import numpy as np

import matplotlib.pyplot as plt

warnings.filterwarnings("ignore")
plt.style.use('fivethirtyeight')

import pandas as pd


import statsmodels.api as sm

import matplotlib
import sys

df = pd.read_csv(str(sys.argv[1])+".csv")

animax = {"Amazon":[1,1,1,1,1,0], "Cisco":[1,1,0,1,1,0], "EA":[1,0,1,1,1,0], "eBay":[1,0,0,1,1,0], "Eros":[0,1,1,1,1,0], "Facebook":[0,0,0,1,1,0], "HP":[0,0,1,1,1,0], "IBM":[1,1,0,1,1,0], "ITC":[1,0,0,1,1,0],"JBL":[1,1,1,1,1,0],"Microsoft":[0,0,0,1,1,0], "MSI":[0,0,1,1,1,0], "Qualcomm":[1,0,1,1,1,0]}


cols =['Open','High','Low','Volume','Ex-Dividend','Split Ratio','Adj. Open','Adj. High','Adj. Low','Adj. Close','Adj. Volume']
df.drop(cols, axis=1, inplace=True)







df.reset_index(inplace=True)
df['Date'] = pd.to_datetime(df['Date'])

df1 = df.set_index('Date')
y=df1
y = df1['Close'].resample('D').mean().fillna(21)
#y.to_csv('data1.csv')



from pylab import rcParams

rcParams['figure.figsize'] = 18, 8

decomposition = sm.tsa.seasonal_decompose(y, model='additive')


#plt.show()

p = d = q = range(0, 2)
pdq = list(itertools.product(p, d, q))
seasonal_pdq = [(x[0], x[1], x[2], 365) for x in list(itertools.product(p, d, q))]


('Examples of parameter combinations for Seasonal ARIMA...')
('SARIMAX: {} x {}'.format(pdq[1], seasonal_pdq[1]))
('SARIMAX: {} x {}'.format(pdq[1], seasonal_pdq[2]))
('SARIMAX: {} x {}'.format(pdq[2], seasonal_pdq[3]))
('SARIMAX: {} x {}'.format(pdq[2], seasonal_pdq[4]))
'''
for param in pdq:
    for param_seasonal in seasonal_pdq:
try:
    mod = sm.tsa.statespace.SARIMAX(y,
                                    order=pdq[2],
                                    seasonal_order=seasonal_pdq[2],
                                    enforce_stationarity=False,
                                    enforce_invertibility=False)

    results = mod.fit()

    ('ARIMA{}x{}12 - AIC:{}'.format(param, param_seasonal, results.aic))
except:
    continue
'''

mod = sm.tsa.statespace.SARIMAX(y,
                                order=(animax[str(sys.argv[1])][0], animax[str(sys.argv[1])][1], animax[str(sys.argv[1])][2]),
                                seasonal_order=(animax[str(sys.argv[1])][3], animax[str(sys.argv[1])][4], animax[str(sys.argv[1])][5], 12),
                                enforce_stationarity=False,
                                enforce_invertibility=False)


results = mod.fit(disp=0)


#print(results.summary().tables[1])

pred = results.get_prediction(start=pd.to_datetime('2017-01-01'), dynamic=False)

pred_ci = pred.conf_int()


#plt.show()

y_forecasted = pred.predicted_mean
y_truth = y['2017':]

#print y_truth,'fkbfuiguofgo'

mse = ((y_forecasted - y_truth) ** 2).mean()
('The Mean Squared Error of our forecasts is {}'.format(round(mse, 2)))

('The Root Mean Squared Error of our forecasts is {}'.format(round(np.sqrt(mse), 2)))


pred_uc = results.get_forecast(steps=100)

pred_ci = pred_uc.conf_int()
a=[]
b=[]
final = []

for j in range(100):
	b.append(pred_ci.iloc[:,1][j])
for j in range(100):
	a.append(pred_ci.iloc[:,0][j])

for i in range(30):
	final.append((float(b[i])+float(a[i]))/2)
xxx = pred_ci.head(30).index.tolist()
dates = []
for i in xxx:
	dates.append(str(i).split(" ")[0])
f = open('a.csv')
last = f.readlines()[-1].split(',')[4]

json = {'forecast':final, 'dates':dates, 'lastValue':last, 'accuracy':round(np.sqrt(mse), 2)}
print json

