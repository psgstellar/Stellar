from rest_framework import serializers


class commitHistory(serializers.Serializer):
    # channel = serializers.CharField(help_text='슬랙채널명')
    # token = serializers.CharField(help_text='Slack토큰')
    latest = serializers.DecimalField(max_digits=10, decimal_places=0, help_text='마지막시간')
    oldest = serializers.DecimalField(max_digits=10, decimal_places=0, help_text='최초시간')

