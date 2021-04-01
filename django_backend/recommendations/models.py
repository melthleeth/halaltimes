from django.db import models

# Create your models here.
class Test(models.Model):
    title = models.CharField(max_length=200)
    content = models.TextField(blank=True)
    # score = models.IntegerField()