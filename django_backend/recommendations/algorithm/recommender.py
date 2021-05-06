import abc
from builtins import isinstance
import pickle

import numpy as np
from . import similarity
from .. import views

class CollaborativeFiltering(object):
    __metaclass__ = abc.ABCMeta
        
    @classmethod
    @abc.abstractmethod
    def buildModel(cls):
        raise NotImplementedError
    
    @classmethod
    @abc.abstractmethod
    def Recommendation(cls):
        raise NotImplementedError
    
    def getNearestNeighbors(self, target, simMeasure, nNeighbors = None):
        similarities = [(simMeasure(self.prefs[target], self.prefs[other]), other) for other in self.prefs if target != other]
        similarities.sort(reverse = True)
        if nNeighbors != None:
            similarities = similarities[0:nNeighbors]
        return similarities
        # similarities = [(similarity, neighbor), ...]
    
   
class ItemBased(CollaborativeFiltering):
        
    def loadData(self, data):
        if isinstance(data, dict):          # If 'data' is preferences on users for training
            self.prefsOnUser = data
            self.prefs = views.transposePrefs(self.prefsOnUser)
        elif isinstance(data, str):         # If 'data' is a file path of training data
            self.prefsOnUser = views.loadData(data)
            self.prefs = views.transposePrefs(self.prefsOnUser)
        self.itemList = self.prefs.keys()
    
    def buildModel(self, simMeasure = similarity.cosine, nNeighbors = 20, pathDump = None):
        model = {}
        for item in self.prefs:
            model.setdefault(item, {})
            correlations = self.getNearestNeighbors(item, simMeasure, nNeighbors)
            for correlation, neighbor in correlations:
                model[item][neighbor] = correlation
        
        # Row normalization
        for c in model:
            COLSUM = sum([model[c][r] for r in model[c]])
            if COLSUM > 0:
                for r in model[c]:
                    model[c][r] /= COLSUM
        
        if pathDump != None:
            self.dumpModel(model, pathDump)
        return model
    
    def Recommendation(self, user, simMeasure = similarity.cosine, nNeighbors = 20, model = None, topN = 10):

        predictedScores = []
        for candidate in self.itemList:
            if candidate in self.prefsOnUser[user]:
                continue
            
            if model != None:
                correlations = model[candidate]
            else:
                correlations = self.getNearestNeighbors(candidate, simMeasure, nNeighbors)
            
            score = sum([correlations[candidate] * self.prefsOnUser[user][item]
                         for item in self.prefsOnUser[user] if candidate in correlations])
            predictedScores.append((score, candidate))
        predictedScores.sort(reverse = True)
        recommendation = [item for similarity, item in predictedScores]
        if topN != None:
            recommendation = recommendation[0:topN]
        return recommendation
