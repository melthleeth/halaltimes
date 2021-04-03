from django.db import models

# Create your models here.
class Pre1(models.Model):
    idpre1 = models.IntegerField(primary_key=True)

    class Meta:
        managed = False
        db_table = 'pre1'


class Pre2(models.Model):
    idpre2 = models.IntegerField(primary_key=True)

    class Meta:
        managed = False
        db_table = 'pre2'


class Pre3(models.Model):
    idpre3 = models.IntegerField(primary_key=True)

    class Meta:
        managed = True
        db_table = 'pre3'

class Pre4(models.Model):
    idpre4 = models.IntegerField(primary_key=True)

    class Meta:
        managed = True
        db_table = 'pre4'

class Pre5(models.Model):
    idpre5 = models.IntegerField(primary_key=True)

