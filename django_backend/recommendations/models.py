from django.db import models

# Create your models here.
class Bookmark(models.Model):
    id_bookmark = models.AutoField(primary_key=True)
    id_store = models.ForeignKey('Store', models.DO_NOTHING, db_column='id_store')
    id_user = models.ForeignKey('User', models.DO_NOTHING, db_column='id_user')
    active = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'bookmark'


class Review(models.Model):
    id_review = models.AutoField(primary_key=True)
    id_user = models.ForeignKey('User', models.DO_NOTHING, db_column='id_user')
    id_store = models.ForeignKey('Store', models.DO_NOTHING, db_column='id_store')
    score = models.FloatField(blank=True, null=True)
    content = models.CharField(max_length=1000, blank=True, null=True)
    upload_date = models.DateTimeField(blank=True, null=True)
    update_date = models.DateTimeField(blank=True, null=True)
    likecnt = models.IntegerField(db_column='likeCnt', blank=True, null=True)  # Field name made lowercase.
    active = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'review'


class ReviewImage(models.Model):
    id_review_image = models.AutoField(primary_key=True)
    id_review = models.ForeignKey(Review, models.DO_NOTHING, db_column='id_review')
    original_image = models.CharField(max_length=1000, blank=True, null=True)
    modified_image = models.CharField(max_length=1000, blank=True, null=True)
    thumb_image = models.CharField(max_length=1000, blank=True, null=True)
    active = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'review_image'


class ReviewLike(models.Model):
    id_review_like = models.AutoField(primary_key=True)
    id_review = models.ForeignKey(Review, models.DO_NOTHING, db_column='id_review')
    id_user = models.ForeignKey('User', models.DO_NOTHING, db_column='id_user')
    active = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'review_like'


class Store(models.Model):
    id_store = models.AutoField(primary_key=True)
    store_name = models.CharField(max_length=50)
    muslim_friendly = models.CharField(max_length=10, blank=True, null=True)
    location_region = models.CharField(max_length=10, blank=True, null=True)
    food_category = models.CharField(max_length=45, blank=True, null=True)
    address = models.CharField(max_length=100, blank=True, null=True)
    tel = models.CharField(max_length=40, blank=True, null=True)
    working_time = models.CharField(max_length=1000, blank=True, null=True)
    days_closed = models.CharField(max_length=45, blank=True, null=True)
    parking = models.IntegerField(blank=True, null=True)
    image = models.CharField(max_length=1000, blank=True, null=True)
    thumb_image = models.CharField(max_length=1000, blank=True, null=True)
    lat = models.CharField(max_length=45, blank=True, null=True)
    lng = models.CharField(max_length=45, blank=True, null=True)
    hits = models.IntegerField(blank=True, null=True)
    active = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'store'


class StoreImage(models.Model):
    id_store_image = models.AutoField(primary_key=True)
    id_store = models.ForeignKey(Store, models.DO_NOTHING, db_column='id_store')
    original_image = models.CharField(max_length=1000, blank=True, null=True)
    active = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'store_image'


class User(models.Model):
    id_user = models.AutoField(primary_key=True)
    email = models.CharField(unique=True, max_length=45)
    password = models.CharField(max_length=100)
    nickname = models.CharField(unique=True, max_length=12)
    born_year = models.CharField(max_length=6)
    gender = models.IntegerField()
    favorites = models.CharField(max_length=100, blank=True, null=True)
    role = models.IntegerField(blank=True, null=True)
    profile_image = models.CharField(max_length=1000, blank=True, null=True)
    active = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'user'


class DjangoUser(models.Model):
    id_django_user = models.AutoField(primary_key=True)
    id_user = models.IntegerField()
    age = models.IntegerField(default=1)
    gender = models.BooleanField()
    is_skeleton = models.BooleanField(default=False)
    review_cnt = models.IntegerField(default=0)
    label = models.IntegerField()

    class Meta:
        managed = True
        db_table = 'django_user'

class DjangoRecomm(models.Model):
    id_django_recomm = models.AutoField(primary_key=True)
    id_store = models.ForeignKey(Store, models.CASCADE, db_column='id_store')
    label = models.IntegerField()

    class Meta:
        managed = True
        db_table = 'django_recomm'
