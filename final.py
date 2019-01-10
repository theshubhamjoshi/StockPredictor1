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

df = pd.read_csv("Amazon.csv")

pdq = {}




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

for param in pdq:
    for param_seasonal in seasonal_pdq:
	try:
	    mod = sm.tsa.statespace.SARIMAX(y,
		                            order=pdq[2],
		                            seasonal_order=seasonal_pdq[2],
		                            enforce_stationarity=False,
		                            enforce_invertibility=False)

	    results = mod.fit()

	    print ('ARIMA{}x{}12 - AIC:{}'.format(param, param_seasonal, results.aic))
	except:
	    continue

