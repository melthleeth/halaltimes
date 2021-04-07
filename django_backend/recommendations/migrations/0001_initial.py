# Generated by Django 3.1.3 on 2021-04-07 04:39

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Bookmark',
            fields=[
                ('id_bookmark', models.AutoField(primary_key=True, serialize=False)),
                ('active', models.IntegerField(blank=True, null=True)),
            ],
            options={
                'db_table': 'bookmark',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='Review',
            fields=[
                ('id_review', models.AutoField(primary_key=True, serialize=False)),
                ('score', models.FloatField(blank=True, null=True)),
                ('content', models.CharField(blank=True, max_length=1000, null=True)),
                ('upload_date', models.DateTimeField(blank=True, null=True)),
                ('update_date', models.DateTimeField(blank=True, null=True)),
                ('likecnt', models.IntegerField(blank=True, db_column='likeCnt', null=True)),
                ('active', models.IntegerField(blank=True, null=True)),
            ],
            options={
                'db_table': 'review',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='ReviewImage',
            fields=[
                ('id_review_image', models.AutoField(primary_key=True, serialize=False)),
                ('original_image', models.CharField(blank=True, max_length=1000, null=True)),
                ('modified_image', models.CharField(blank=True, max_length=1000, null=True)),
                ('thumb_image', models.CharField(blank=True, max_length=1000, null=True)),
                ('active', models.IntegerField(blank=True, null=True)),
            ],
            options={
                'db_table': 'review_image',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='ReviewLike',
            fields=[
                ('id_review_like', models.AutoField(primary_key=True, serialize=False)),
                ('active', models.IntegerField(blank=True, null=True)),
            ],
            options={
                'db_table': 'review_like',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='Store',
            fields=[
                ('id_store', models.AutoField(primary_key=True, serialize=False)),
                ('store_name', models.CharField(max_length=50)),
                ('muslim_friendly', models.CharField(blank=True, max_length=10, null=True)),
                ('location_region', models.CharField(blank=True, max_length=10, null=True)),
                ('food_category', models.CharField(blank=True, max_length=45, null=True)),
                ('address', models.CharField(blank=True, max_length=100, null=True)),
                ('tel', models.CharField(blank=True, max_length=40, null=True)),
                ('working_time', models.CharField(blank=True, max_length=1000, null=True)),
                ('days_closed', models.CharField(blank=True, max_length=45, null=True)),
                ('parking', models.IntegerField(blank=True, null=True)),
                ('image', models.CharField(blank=True, max_length=1000, null=True)),
                ('thumb_image', models.CharField(blank=True, max_length=1000, null=True)),
                ('lat', models.CharField(blank=True, max_length=45, null=True)),
                ('lng', models.CharField(blank=True, max_length=45, null=True)),
                ('hits', models.IntegerField(blank=True, null=True)),
                ('active', models.IntegerField(blank=True, null=True)),
            ],
            options={
                'db_table': 'store',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='StoreImage',
            fields=[
                ('id_store_image', models.AutoField(primary_key=True, serialize=False)),
                ('original_image', models.CharField(blank=True, max_length=1000, null=True)),
                ('active', models.IntegerField(blank=True, null=True)),
            ],
            options={
                'db_table': 'store_image',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='User',
            fields=[
                ('id_user', models.AutoField(primary_key=True, serialize=False)),
                ('email', models.CharField(max_length=45, unique=True)),
                ('password', models.CharField(max_length=100)),
                ('nickname', models.CharField(max_length=12, unique=True)),
                ('born_year', models.CharField(max_length=6)),
                ('gender', models.IntegerField()),
                ('favorites', models.CharField(blank=True, max_length=100, null=True)),
                ('role', models.IntegerField(blank=True, null=True)),
                ('profile_image', models.CharField(blank=True, max_length=1000, null=True)),
                ('active', models.IntegerField(blank=True, null=True)),
            ],
            options={
                'db_table': 'user',
                'managed': False,
            },
        ),
        migrations.CreateModel(
            name='DjangoUser',
            fields=[
                ('id_django_user', models.AutoField(primary_key=True, serialize=False)),
                ('id_user', models.IntegerField()),
                ('age', models.IntegerField(default=1)),
                ('gender', models.BooleanField()),
                ('is_skeleton', models.BooleanField(default=False)),
                ('review_cnt', models.IntegerField(default=0)),
                ('label', models.IntegerField()),
            ],
            options={
                'db_table': 'django_user',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='DjangoRecomm',
            fields=[
                ('id_django_recomm', models.AutoField(primary_key=True, serialize=False)),
                ('label', models.IntegerField()),
                ('id_store', models.ForeignKey(db_column='id_store', on_delete=django.db.models.deletion.CASCADE, to='recommendations.store')),
            ],
            options={
                'db_table': 'django_recomm',
                'managed': True,
            },
        ),
    ]
